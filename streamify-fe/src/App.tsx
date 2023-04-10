import React from 'react';
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

function App() {
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


            </Routes>
          </BrowserRouter>        
        <Footer/>
      </>
  );
}

export default App;
