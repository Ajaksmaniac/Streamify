import axios from "axios"

export const getUserById = (userId:number)=>{
    return axios.get(`${process.env.REACT_APP_BE_SERVER}${process.env.REACT_APP_GET_USERNAME_ENDPOINT}${userId}`)
}