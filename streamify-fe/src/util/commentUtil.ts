import axios from "axios"

export const getCommentsForVideo = (videoId:number)=>{
    return axios.get(`${process.env.REACT_APP_BE_SERVER}${process.env.REACT_APP_GET_VIDEO_COMMENTS_ENDPOINT}${videoId}`)
}
export const deleteComment = (commentId:number, token: string)=>{
    return axios.delete(`${process.env.REACT_APP_BE_SERVER}${process.env.REACT_APP_DELETE_COMMENT_ENDPOINT}${commentId}`,{
        headers:{
            Authorization: `Bearer ${token}`
        }
    })
}
export const addCommentForVideo = (videoId:number, content: string,token: string)=>{
    return axios.post(`${process.env.REACT_APP_BE_SERVER}${process.env.REACT_APP_POST_VIDEO_COMMENT_ENDPOINT}`,
    {
        content:content,
        videoId: videoId
        
    },
        {headers:{
            Authorization: `Bearer ${token}`
        }})
}