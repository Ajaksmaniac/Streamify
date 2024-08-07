import React, { useState } from 'react';
import { Button, Container, Form } from 'react-bootstrap';
import { loginWithUsernameAndPassword } from '../util/authUtil';
import { useAuth } from '../hooks/useAuth';

const Login = () => {
    const auth = useAuth();
    const [username,setUsername] = useState("");
    const [password,setPassword] = useState("");
    const [errorHidden,setErrorHidden] = useState(true);
    const handleChange = (e:any) =>{
        if(e.target.name == 'username') setUsername(e.target.value)
        if(e.target.name == 'password') setPassword(e.target.value)

    }

    const submit = (e:any) =>{
        e.preventDefault()

        loginWithUsernameAndPassword(username,password).then(res=>{

            if(res.status == 200){
                auth.signin(username,password)
            }
          
        }).catch(() =>setErrorHidden(false))
    }
    
    return (
        <Container className='mt-5 w-25'>
            <h1>Sign In</h1>
             <Form>
                {!errorHidden && (
                    <Form.Group>
                        <Form.Label className='text-danger'>Username or password are incorrect</Form.Label>
                    </Form.Group>)
                }
             
            <Form.Group className="mb-3" controlId="formUsername">
                <Form.Label>Username</Form.Label>
                <Form.Control type="text" placeholder="Enter username"
                    name='username'
                    value={username} 
                    onChange={(e)=>handleChange(e)}/>
            </Form.Group>

            <Form.Group className="mb-3" controlId="formBasicPassword">
                <Form.Label>Password</Form.Label>
                <Form.Control type="password" placeholder="Password" 
                    name='password'
                    value={password} 
                    onChange={(e)=>handleChange(e)}/>
                <Form.Text className="text-muted">
                We'll never share your password with anyone else.
                </Form.Text>
            </Form.Group>
           
            

            <Button variant="primary" type="submit" onClick={(e) => submit(e)}>
                Login
            </Button>
        </Form>
        </Container>

       
    );
};

export default Login;


