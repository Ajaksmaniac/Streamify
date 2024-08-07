import React, { useState } from 'react';
import { Button, Container, Form } from 'react-bootstrap';
import { registerWithUsernameAndPassword } from '../util/authUtil';
import { useAuth } from '../hooks/useAuth';
import { FOCUSABLE_SELECTOR } from '@testing-library/user-event/dist/utils';

const RegisterPage = () => {

    const auth = useAuth();
    const [username,setUsername] = useState("");
    const [password,setPassword] = useState("");
    const [confirmPassword,setConfirmPassword] = useState("");
    const [showRegisterError,setShowRegisterError] = useState(false);
    const [showConfirmPasswordError,setShowConfirmPasswordError] = useState(false);
    const [showFillAllFieldsError,setShowFillAllFieldsError] = useState(false);

    const handleChange = (e:any) =>{
        if(e.target.name == 'username') setUsername(e.target.value)
        if(e.target.name == 'password') setPassword(e.target.value)
        if(e.target.name == 'confirmPassword') setConfirmPassword(e.target.value)
        // if(confirmPassword !== "" && password !== ""   && confirmPassword === password) {setConfirmPasswordErrorHidden(true)} else {setConfirmPasswordErrorHidden(false)}
    }

    const submit = (e:any) =>{
        if(username == "" || password == "" || confirmPassword == ""){
            setShowFillAllFieldsError(true)
            return
        } 
        if(password != confirmPassword) setShowConfirmPasswordError(true)


        // auth.signup(username,password)
        e.preventDefault()
        // if(confirmPassword !== password) setConfirmPasswordErrorHidden(false)
        registerWithUsernameAndPassword(username,password).then(res=>{

            if(res.status == 200){
                auth.signup(username,password)
            }
          
        }).catch(() =>setShowRegisterError(true))
    }

    return (
        <Container className='mt-5 w-25'>
            <h1>Sign Up</h1>
             <Form>
             {showRegisterError && (
                    <Form.Group>
                        <Form.Label className='text-danger'>Username not available, try another.</Form.Label>
                    </Form.Group>)
                }
            <Form.Group className="mb-3" controlId="formUsername">
                <Form.Label>Username</Form.Label>
                <Form.Control type="username" placeholder="Enter username" name="username" value={username} onChange={(e)=>handleChange(e)} required/>
            </Form.Group>

            <Form.Group className="mb-3" controlId="formBasicPassword">
                <Form.Label>Password</Form.Label>
                <Form.Control type="password" placeholder="Password" name="password" data-testId="pass" value={password} onChange={(e)=>handleChange(e)} required/>
                <Form.Text className="text-muted">
                We'll never share your password with anyone else.
                </Form.Text>
            </Form.Group>

            <Form.Group className="mb-3" controlId="formBasicConfirmPassword">
                <Form.Label>Confirm Password</Form.Label>
                <Form.Control type="password" placeholder="Password" name="confirmPassword" data-testId="confirmPass" value={confirmPassword} onChange={(e)=>handleChange(e)} required/>
                
            </Form.Group>

            {showConfirmPasswordError && (
                    <Form.Group>
                        <Form.Label className='text-danger'>Passwords do not match.</Form.Label>
                    </Form.Group>)
            }

            {showFillAllFieldsError && (
                    <Form.Group>
                        <Form.Label className='text-danger'>Fill out all the fields.</Form.Label>
                    </Form.Group>)
            }
            <Button variant="primary" type="submit"
            onClick={(e) => submit(e)}>
                Register
            </Button>
        </Form>
        </Container>

       
    );
};

export default RegisterPage;