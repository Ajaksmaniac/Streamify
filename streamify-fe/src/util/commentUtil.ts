import axios from "axios"

export const getCommentsForVideo = (videoId:number)=>{
    return axios.get(`${process.env.REACT_APP_BE_SERVER}${process.env.REACT_APP_GET_VIDEO_COMMENTS_ENDPOINT}${videoId}`)
}