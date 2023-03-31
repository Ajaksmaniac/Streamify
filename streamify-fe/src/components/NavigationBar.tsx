import { Navbar, Nav, Form, FormControl, Button, Row, FormGroup, Col, FormCheck } from 'react-bootstrap';
import { Link, useSearchParams } from 'react-router-dom';
import {NavbarProps } from './../constants/types';
import Image from 'react-bootstrap/Image'
import { useEffect, useState } from 'react';
// import { URLSearchParams } from 'url';

export default function NavigationBar(props:NavbarProps){
    // const [searchParams, setSearchParams] = useSearchParams();
    const [disabled,setDisabled] = useState(false)
    const [keywords,setKeywords] = useState(new URLSearchParams(window.location.search).get('keywords') as string | number | string[] | undefined);
    const [searchVideos,setSearchVideos] = useState(new URLSearchParams(window.location.search).get('videos')?true:false);

    const [searchChannels,setSearchChannels] = useState(new URLSearchParams(window.location.search).get('channels')?true:false);

    useEffect(()=>{
        if(searchVideos ==  false && searchChannels == false || (keywords === '' || keywords == null)){
            setDisabled(true)
        } else  setDisabled(false)
    },[keywords,searchVideos,searchChannels])

    const handleChange = (e:any) => {
        if(e.target.name == 'keywords') setKeywords(e.target.value)
        if(e.target.name == 'videos') setSearchVideos(!searchVideos)
        if(e.target.name == 'channels') setSearchChannels(!searchChannels)
    }
    return(
        <Navbar bg="dark" variant="dark" >

            <img src='Logo.png' className='img-fluid shadow-4' alt='...' />

            <Form action="/search"  className='mx-auto'>
                <Row>
                    <Form.Group as={Col} >
                        <FormControl type="text" placeholder="Search Video"  name="keywords" value={keywords} onChange={(e)=>handleChange(e)} style={{width:'500px'}} />
                    </Form.Group>
                    <Form.Group as={Col} >
                        <Form.Check 
                            type='checkbox'
                            id='default-checkbox'
                            label='Videos'
                            className="text-primary"
                            name='videos'
                            onChange={(e)=>handleChange(e)}
                            checked={searchVideos}
                        />   
                        <Form.Check 
                            type='checkbox'
                            id='default-checkbox'
                            label='Channels'
                            className="text-primary"
                            name='channels'
                            onChange={(e)=>handleChange(e)}
                            checked={searchChannels}
                        />
                    </Form.Group>
                    <Form.Group as={Col} >
                        <Button variant="outline-info" type="submit" disabled={disabled}>Search</Button>            
                    </Form.Group>


                </Row>
            </Form>
           
            <Nav className="ml-auto">
                <Row>
                    <Form.Group as={Col}  >
                            <Button variant="primary" type="submit" >Login</Button>            
                        
                            <Button variant="info" type="submit" >Sign Up</Button>            
                    </Form.Group>
                </Row>
               
            </Nav>

        </Navbar>
    )
}