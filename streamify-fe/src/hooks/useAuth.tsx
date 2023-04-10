import React, { useState, useEffect, useContext, createContext } from "react";
import { loginWithUsernameAndPassword } from "../util/authUtil";
import { User } from "../constants/types";
import { getUserById } from "../util/userUtil";


const AuthContext = createContext({} as {
  user: ()=> User | null
  signin: (username: string, password: string) => void;
  signup: (username: string, password: string) => void;
  signout: () => void});
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
                refreshToken: res1.data.refreshToken
              }

              localStorage.setItem('user',JSON.stringify(user))
              document.location.href = window.origin;
            }
          })


        


          // setUser(user)

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
    };
  }