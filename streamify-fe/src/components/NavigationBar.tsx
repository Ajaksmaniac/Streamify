import { Navbar, Nav, Form, FormControl, Button, Row, FormGroup, Col } from 'react-bootstrap';
import { Link, useNavigate } from 'react-router-dom';
import {NavbarProps } from './../constants/types';
import Image from 'react-bootstrap/Image'

export default function NavigationBar(props:NavbarProps){
    const navigate = useNavigate();

    return(
        <Navbar bg="dark" variant="dark" >
        {/* <Link to={""} className="navbar-brand">
            <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/6/69/IMDB_Logo_2016.svg/200px-IMDB_Logo_2016.svg.png" width="45" height="30" alt="brand"/>Mini
        </Link> */}
            <img src='Logo.png' className='img-fluid shadow-4' alt='...' />

            <Form action="/search"  className='mx-auto'>
                <Row>
                    <Form.Group as={Col} >
                        <FormControl type="text" placeholder="Search Video"  name="name" style={{width:'500px'}}/>
                    </Form.Group>

                    <Form.Group as={Col} >
                        <Button variant="outline-info" type="submit">Search</Button>            
                    </Form.Group>

                </Row>
            </Form>
           
            <Nav className="ml-auto">
                <Row>
                    <Form.Group as={Col}  >
                            <Button variant="primary" type="submit" onClick={() => navigate("/login" )}>Login</Button>            
                        
                            <Button variant="info" type="submit" >Sign Up</Button>            
                    </Form.Group>
                </Row>
               
            </Nav>

        </Navbar>
    )
}