import React from 'react';
// import { match } from 'react-router-dom';
import { BrowserRouter, Routes, Route}  from 'react-router-dom';
import Footer from './components/Footer';
import NavigationBar from './components/NavigationBar';
import { AuthContext } from './context/AuthContext';
import { useAuth } from './hooks/useAuth';
import { HomePage } from './views/HomePage';
import LoginPage from './views/LoginPage';
import VideoPage from './views/VideoPage';

function App() {
  const { user } = useAuth();
  return (
      <>
          <BrowserRouter>
          <AuthContext.Provider value={{ user, setUser(user) {
            return null
          }, }}>
          <NavigationBar logged={false} isAdmin={false}/>

            <Routes>
              <Route path="/" element={<HomePage />} />
              <Route path="/login" element={<LoginPage />} />
              <Route path="/video/:id" element={<VideoPage />} />
            </Routes>
            <Footer/>
            </AuthContext.Provider>
          </BrowserRouter>        

      </>
  );
}

export default App;
