import React, { useState, useEffect, useContext, createContext } from "react";
import { loginWithUsernameAndPassword } from "../util/authUtil";
import { User } from "../constants/types";
import { getUserById } from "../util/userUtil";
import { NonNullChain } from "typescript";
import { AnyARecord } from "dns";

// // Establish WebSocket connection
// var socket = io('http://localhost:8080/websocket', {
//   auth: {
//     token: 'YOUR_AUTH_TOKEN',
//   },
// });

// // Subscribe to channel
// socket.emit('subscribe', { channelId: 'YOUR_CHANNEL_ID' });




const AuthContext = createContext({} as {
  user: ()=> User | null
  signin: (username: string, password: string) => void;
  signup: (username: string, password: string) => void;
  signout: () => void;
  reload: ()=> void;
})
// Provider component that wraps your app and makes auth object ...
// ... available to any child component that calls useAuth().
export function ProvideAuth({ children }: any) {
  const auth = useProvideAuth();
  return <AuthContext.Provider value={auth}>{children}</AuthContext.Provider>;
}
export const useAuth = () => {
  return useContext(AuthContext);
};

function useProvideAuth() {

    const signin = (username:string, password:string) => {
      loginWithUsernameAndPassword(username,password).then(res1=>{
        if(res1.status == 200){

          getUserById(res1.data.userId).then(res2=>{
            if(res2.status == 200){
              const user: User = {
                id: res1.data.userId,
                accessToken: res1.data.accessToken,
                username: res2.data.username,
                role: res2.data.role,
                refreshToken: res1.data.refreshToken,
                subscribedChannels: res2.data.subscribedChannels
              }
              

              localStorage.setItem('user',JSON.stringify(user))
              document.location.href = window.origin;
            }
          })
        }
      })
    };
    const reload = () => {
      

          getUserById(user()?.id!).then(res=>{
            if(res.status == 200){
              // const user:User = JSON.parse(localStorage.getItem("user")| "");

              const value = localStorage.getItem("user")

              if (typeof value === 'string') {
                  const user:User = JSON.parse(value) // ok
                  user!.subscribedChannels! = res.data.subscribedChannels

                  // localStorage.removeItem('user')
                  localStorage.setItem('user',JSON.stringify(user))
    
                  // document.location.href = window.origin;

              }
              // const user: User = {
              //   id: res.data.userId,
                
              //   username: res.data.username,
              //   role: res.data.role,
                
              //   subscribedChannels: res.data.subscribedChannels
              // }
              
            }
          })
        
      
    };
    const signup = () => {
      document.location.href = window.origin+"/login"
    };
    const signout = () => {
     localStorage.removeItem('user')
     document.location.href = window.origin;

    };

    const user = () : User|null=>{
      return JSON.parse(localStorage.getItem('user')!)
      

    }

    return {
      user,
      signin,
      signup,
      signout,
      reload
    };
  }