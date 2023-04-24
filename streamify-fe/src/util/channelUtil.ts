import axios from "axios"

export const getChannelDetails = (chanelId:number)=>{
    return axios.get(`${process.env.REACT_APP_BE_SERVER}${process.env.REACT_APP_GET_CHANNEL_DETAILS_ENDPOINT}${chanelId}`)
}

export const getChannelDetailsByUserId = (userId:number)=>{
    return axios.get(`${process.env.REACT_APP_BE_SERVER}${process.env.REACT_APP_GET_USERS_CHANNELS_DETAILS_ENDPOINT}${userId}`)
}

export const searchChannelsByKeywords = (keywords: string)=>{
    return axios.get(`${process.env.REACT_APP_BE_SERVER}${process.env.REACT_APP_GET_CHANNEL_SEARCH_ENDPOINT}`,{params:{keywords:keywords}})
}

export const createChannel = (channelName: String, username: string, token: string)=>{
    console.log(`${process.env.REACT_APP_BE_SERVER}${process.env.REACT_APP_POST_CREATE_CHANNEL_ENDPOINT}`)
    return axios.post(`${process.env.REACT_APP_BE_SERVER}${process.env.REACT_APP_POST_CREATE_CHANNEL_ENDPOINT}`,
    {
        channelName:channelName, username: username
        
    },
        {headers:{
            Authorization: `Bearer ${token}`
        }}
    
    )
}