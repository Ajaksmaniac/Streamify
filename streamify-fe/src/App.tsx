import React from 'react';
import { RouterProvider, createBrowserRouter}  from 'react-router-dom';
import Footer from './components/Footer';
import NavigationBar from './components/NavigationBar';
import { HomePage } from './views/HomePage';


const videos = [
  {
    title: 'Video 1',
    author: 'Author 1',
    views: '1,000,000',
    image: 'https://via.placeholder.com/150',
  },
  {
    title: 'Video 2',
    author: 'Author 2',
    views: '500,000',
    image: 'https://via.placeholder.com/150',
  },
  {
    title: 'Video 3',
    author: 'Author 3',
    views: '250,000',
    image: 'https://via.placeholder.com/150',
  },
  {
    title: 'Video 2',
    author: 'Author 2',
    views: '500,000',
    image: 'https://via.placeholder.com/150',
  },
  {
    title: 'Video 3',
    author: 'Author 3',
    views: '250,000',
    image: 'https://via.placeholder.com/150',
  },
  
  {
    title: 'Video 3',
    author: 'Author 3',
    views: '250,000',
    image: 'https://via.placeholder.com/150',
  },
  {
    title: 'Video 2',
    author: 'Author 2',
    views: '500,000',
    image: 'https://via.placeholder.com/150',
  },
  {
    title: 'Video 3',
    author: 'Author 3',
    views: '250,000',
    image: 'https://via.placeholder.com/150',
  },
  
];

const router = createBrowserRouter([
  {
    path: "/",
    element: <HomePage videos={videos}/>
  },
]);
function App() {
  return (

      <>
        <NavigationBar logged={false} isAdmin={false}/>
        <RouterProvider router={router}/>
        <Footer/>
      </>
  );
}

export default App;
