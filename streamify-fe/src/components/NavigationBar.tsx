import { Navbar, Nav, Form, FormControl, Button, Row, FormGroup, Col, FormCheck } from 'react-bootstrap';
import { Link, useNavigate, useSearchParams } from 'react-router-dom';
import {NavbarProps } from './../constants/types';
import Image from 'react-bootstrap/Image'
import { useEffect, useState } from 'react';
import { useAuth } from '../hooks/useAuth';
import NewChannelButton from './NewChannelButton';
import ChannelsDropdown from './ChannelsDropdown';
import { getChannelDetailsByUserId } from '../util/channelUtil';
// import { URLSearchParams } from 'url';

export default function NavigationBar(props:NavbarProps){
    // const [searchParams, setSearchParams] = useSearchParams();
    const auth = useAuth();
    // console.log(auth.user())

    const navigate = useNavigate();
    const keywordsInitial = new URLSearchParams(window.location.search).get('keywords') as string | number | string[] | undefined
    const [disabled,setDisabled] = useState(false)
    const [keywords,setKeywords] = useState(keywordsInitial? keywordsInitial:'');
    const [searchVideos,setSearchVideos] = useState(new URLSearchParams(window.location.search).get('videos')?true:false);
    const [userChannels, setUserChannels] = useState([])
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

    
    useEffect(()=>{
        if(auth.user()){
            getChannelDetailsByUserId(auth.user()?.id!).then(res=>{
                setUserChannels(res.data)
            }).catch(e=>{
                setUserChannels([])
            })
        }
    },[])
       
    

    return(
        <>
        <Navbar bg="dark" variant="dark" >

            <img src={`${window.origin}/Logo.png`} className='img-fluid shadow-4' alt='...' />

            <Form action="/search"  className='mx-auto'>
                <Row>
                    <Form.Group as={Col} >
                        <FormControl type="text" placeholder="Search Video"  name="keywords" value={keywords} onChange={(e)=>handleChange(e)} style={{width:'500px'}} />
                    </Form.Group>
                    <Form.Group as={Col} >
                        <Form.Check 
                            type='checkbox'
                            // id='default-checkbox'
                            label='Videos'
                            className="text-primary"
                            name='videos'
                            onChange={(e)=>handleChange(e)}
                            checked={searchVideos}
                        />   
                        <Form.Check 
                            type='checkbox'
                            // id='default-checkbox'
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
        {auth.user()? (<Nav className="ml-auto">
                <Row>

                    {/* Add become conntent creator button */}
                    
                    <Form.Group as={Col}   className='p-2'>
                    
              
                    <Form.Text>Hello {auth.user()?.role?.name}, {auth.user()?.username}</Form.Text>
                    <span>&nbsp;&nbsp;</span>
                            <Button 
                            className='m-1' 
                            variant="primary" 
                            type="submit"
                            onClick={auth.signout}
                            >Logout</Button>            
                        
                            
                    </Form.Group>
                    
                </Row>
               
            </Nav>):(<Nav className="ml-auto">
                <Row>
                    <Form.Group as={Col}   className='p-2'>
                            <Button 
                            className='m-1' 
                            variant="primary" 
                            type="submit"
                            onClick={() => navigate('login')}
                            >Login</Button>            
                        
                            <Button 
                            variant="info" 
                            type="submit" 
                            onClick={() => navigate('register')}>Sign Up</Button>            
                    </Form.Group>
                </Row>
               
            </Nav>) }
                

        

        </Navbar>
        {auth.user()?.role?.name === 'content_creator' && 
            <Navbar bg="primary" variant="dark">
                <span>&nbsp;&nbsp;</span>

                <h3>Content Creator tools</h3>
                <span>&nbsp;&nbsp;</span>

                <NewChannelButton/>
                <span>&nbsp;&nbsp;</span>

                {userChannels.length > 0 &&
                <ChannelsDropdown channels={userChannels}/>
                }
                
            </Navbar>
        }
        {auth.user()?.role?.name === 'admin' && 
            <Navbar bg="primary" variant="dark">
                <span>&nbsp;&nbsp;</span>

                <h3>Content Creator tools</h3>
                <span>&nbsp;&nbsp;</span>

                <NewChannelButton/>
                <span>&nbsp;&nbsp;</span>

                {userChannels.length > 0 &&
                <ChannelsDropdown channels={userChannels}/>
                }
                
            </Navbar>
        }
        </>
    )
}


