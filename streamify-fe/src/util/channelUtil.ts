import axios from "axios"

export const getChannelDetails = (chanelId:number)=>{
    return axios.get(`${process.env.REACT_APP_BE_SERVER}${process.env.REACT_APP_GET_CHANNEL_DETAILS_ENDPOINT}${chanelId}`)
}

export const searchChannelsByKeywords = (keywords: string)=>{
    return axios.get(`${process.env.REACT_APP_BE_SERVER}${process.env.REACT_APP_GET_CHANNEL_SEARCH_ENDPOINT}`,{params:{keywords:keywords}})
}