import React, { useEffect } from 'react';
// import { match } from 'react-router-dom';
import { BrowserRouter, Routes, Route}  from 'react-router-dom';
import Footer from './components/Footer';
import NavigationBar from './components/NavigationBar';
import { HomePage } from './views/HomePage';
import { SearchPage } from './views/SearchPage';
import VideoPage from './views/VideoPage';
import ChannelPage from './views/ChannelPage';
import RegisterPage from './views/RegisterPage';
import LoginPage from './views/LoginPage';
import CreateChannelPage from './views/CreateChannelPage';
import NotFoundPage from './views/NotFoundPage';
import { useAuth } from './hooks/useAuth';
// import { io } from 'socket.io-client';

function App() {
  // const auth = useAuth()
  // window.WebSocket = window.WebSocket || (window as any).MozWebSocket!;
  // useEffect(()=>{
  //   if(auth.user()){
  //     const socket = new WebSocket(`ws://localhost:8080/notifications?userId=${auth.user()?.id}`);
  
  //       socket.onopen = () => {
  //         console.log('WebSocket connection established!');
  //       };
  
  //       socket.onmessage = (event) => {
  //         const receivedMessage = event.data;
  //         console.log('Received message:', receivedMessage);
  //       };
  
  //       socket.onclose = () => {
  //         console.log('WebSocket connection closed!');
  //       };
  //    }

  // },[])

  return (
      <>
      
          <BrowserRouter>
          <NavigationBar logged={false} isAdmin={false}/>

            <Routes>
              <Route path="/" element={<HomePage />} />
              <Route path="/search" element={<SearchPage />} />
              <Route path="/video/:id" element={<VideoPage />} />
              <Route path="/channel/:id" element={<ChannelPage />} />
              <Route path="/register" element={<RegisterPage />} />
              <Route path="/login" element={<LoginPage />} />
              <Route path="/addChannel" element={<CreateChannelPage />} />
              <Route path='*' element={<NotFoundPage />}/>
            </Routes>
          </BrowserRouter>        
        <Footer/>
      </>
  );
}

export default App;
