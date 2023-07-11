import axios from "axios"

export const getUserById = (userId:number)=>{
    return axios.get(`${process.env.REACT_APP_BE_SERVER}${process.env.REACT_APP_GET_USERNAME_ENDPOINT}${userId}`)
}

export const subscribe = (userId:number, channelId:number,accessToken:string)=>{
    return axios.post(`${process.env.REACT_APP_BE_SERVER}${process.env.REACT_APP_POST_SUBSCRIBE_ENDPOINT}`,{},{
        headers: {
          Authorization: `Bearer ${accessToken}`,
          channel: channelId,

        },
      })
}


export const unscubscribe = (userId:number, channelId:number,accessToken:string)=>{
    return axios.post(`${process.env.REACT_APP_BE_SERVER}${process.env.REACT_APP_POST_UNSUBSCRIBE_ENDPOINT}`,{},{
        headers: {
          Authorization: `Bearer ${accessToken}`,
          channel: channelId,
        },
      })
}