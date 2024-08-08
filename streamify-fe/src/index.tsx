import React from 'react';
import { createRoot } from 'react-dom/client';
import App from './App';
import reportWebVitals from './reportWebVitals';
import 'bootstrap/dist/css/bootstrap.min.css';
import { ProvideAuth } from './hooks/useAuth';

// require('dotenv').config()

const root = createRoot(
  document.getElementById('root') as HTMLElement
);
// const { user,addUser } = useAuth();

root.render(
  <React.StrictMode>
     <ProvideAuth><App /></ProvideAuth>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
