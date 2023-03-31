import React from 'react';
// import { match } from 'react-router-dom';
import { BrowserRouter, Routes, Route}  from 'react-router-dom';
import Footer from './components/Footer';
import NavigationBar from './components/NavigationBar';
import { HomePage } from './views/HomePage';
import { SearchPage } from './views/SearchPage';
import VideoPage from './views/VideoPage';

function App() {
  return (
      <>
          <BrowserRouter>
          <NavigationBar logged={false} isAdmin={false}/>

            <Routes>
              <Route path="/" element={<HomePage />} />
              <Route path="/search" element={<SearchPage />} />
              <Route path="/video/:id" element={<VideoPage />} />
            </Routes>
          </BrowserRouter>        
        <Footer/>
      </>
  );
}

export default App;
