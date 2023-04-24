import axios from "axios"

export const getAllVideos = ()=>{
return axios.get(`${process.env.REACT_APP_BE_SERVER}${process.env.REACT_APP_GET_ALL_VIDEOS_ENDPOINT}`)
}

export const searchVideosByKeywords = (keywords: string)=>{
    return axios.get(`${process.env.REACT_APP_BE_SERVER}${process.env.REACT_APP_GET_VIDEO_SEARCH_ENDPOINT}`,{params:{keywords:keywords}})
}

export const searchGetVideosForChannel = (channelId: number)=>{
    return axios.get(`${process.env.REACT_APP_BE_SERVER}${process.env.REACT_APP_GET_VIDEOS_FOR_CHANNEL_ENDPOINT}${channelId}`)
}

export const getVideoDetailsById = (videoId: number|string)=>{
    return axios.get(`${process.env.REACT_APP_BE_SERVER}${process.env.REACT_APP_GET_VIDEO_DETAILS_ENDPOINT}${videoId}`)
}