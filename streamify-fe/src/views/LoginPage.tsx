import axios from "axios";
import React, { useEffect, useState } from "react";
import { Button, Container, Form } from "react-bootstrap";
import { useAuth } from "../hooks/useAuth";

const LoginPage = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const { login } = useAuth();


  const handleUsernameChange = (e: any) => {
    setUsername(e.target.value);
  };

  const handlePasswordChange = (e: any) => {
    setPassword(e.target.value);
  };

  const handleLogin = () =>{
    const data = {
        username: username,
        password: password
    }

    axios.post("http://localhost:8080/auth/login", data).then(res =>{
        if(res.status == 200){

            console.log(res.data)
            login({
              userId: res.data.userId,
              authToken: res.data.authToken,
              refreshToken: res.data.refreshToken
            })
            return;
        }

        throw new Error("There was an error during loging!")
        
    })
    

  }

  return (
    <Container className="mt-4 w-25">
      <Form>
        <Form.Group className="mb-3" controlId="formBasicUsernamel">
          <Form.Label>Username</Form.Label>
          <Form.Control
            type="username"
            placeholder="Enter Username"
            value={username}
            onChange={(u) => handleUsernameChange(u)}
          />
        </Form.Group>

        <Form.Group className="mb-3" controlId="formBasicPassword">
          <Form.Label>Password</Form.Label>
          <Form.Control
            type="password"
            placeholder="Password"
            value={password}
            onChange={(u) => handlePasswordChange(u)}
          />
        </Form.Group>

        <Button variant="primary" type="button" onClick={ handleLogin}>
          Submit
        </Button>
      </Form>
    </Container>
  );
};

export default LoginPage;
