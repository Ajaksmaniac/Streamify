import axios from "axios";


export const loginWithUsernameAndPassword = (username:string,password:string)=>{
    return axios.post(`${process.env.REACT_APP_BE_SERVER}${process.env.REACT_APP_AUTH_LOGIN_ENDPOINT}`,{username:username,password:password})
}

export const registerWithUsernameAndPassword = (username:string,password:string)=>{
    return axios.post(`${process.env.REACT_APP_BE_SERVER}${process.env.REACT_APP_AUTH_REGISTER_ENDPOINT}`,{username:username,password:password})
}