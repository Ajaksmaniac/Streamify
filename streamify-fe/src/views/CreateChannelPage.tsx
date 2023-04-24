import React, { useEffect, useState } from 'react';
import { Button, Container, Form } from 'react-bootstrap';
import { useAuth } from '../hooks/useAuth';
import { createChannel } from '../util/channelUtil';
import { useNavigate } from 'react-router-dom';

const CreateChannelPage = () => {
    const auth = useAuth();
    const navigate = useNavigate();
    
    useEffect(()=>{
        if(!auth.user()){
            navigate("*")
        }
    },[])
    const [error,setError] = useState("")
    const [username,setUsername] = useState(auth.user()?.username!)
    const [channelName,setChannelName] = useState("")
    const handleChange = (e: any) =>{
        console.log(e)
        if(e.target.id=="username"){
            setUsername(e.target.value)
        }
        if(e.target.id=="channelName"){
            setChannelName(e.target.value)
        }
    }
    const handleCheckChange = (e: any)=>{
        if(e.target.checked){
            setUsername(auth.user()?.username!)
        }else{
            setUsername("")
        }

    }

    const handleSubmit = (e: any) =>{
        e.preventDefault();
        const channelName = e.target.elements["channelName"].value;
        createChannel(channelName,username, auth.user()?.accessToken!).then(res=>{
            navigate(`/channel/${res.data.id}`)
        }).catch(e=>{
            setError(e.response.data.detail)
        })
    }
    return (
        <Container>
            {auth.user() && (auth.user()?.role?.name == "content_creator"|| auth.user()?.role?.name == "admin") && (
                <Form onSubmit={e=>handleSubmit(e)}>
                <Form.Group className="mb-3" controlId="channelName">
                <Form.Label>Channel Name</Form.Label>
                <Form.Control type="text" placeholder="Enter new channel name" value={channelName} onChange={e=>handleChange(e)}/>
                {auth.user()?.role?.name == "admin" && (
                    <Form.Group className="mb-3" controlId="username">
                        <Form.Label>Channel Owner username</Form.Label>
                        <Form.Control type="text" placeholder="Username" value={username} onChange={e=>handleChange(e)}/>
                        <Form.Check type="checkbox" label="Create for me" onChange={e=>handleCheckChange(e)} defaultChecked={true}/>
                    </Form.Group>
                )}
                {error && (
                     <Form.Text className="text-danger">
                     {error}
                     </Form.Text>
                )}
               
                </Form.Group>
                <Button variant="primary" type="submit">
                    Submit
                </Button>
            </Form>
            )}
            
        </Container>
      
    );
};

export default CreateChannelPage;