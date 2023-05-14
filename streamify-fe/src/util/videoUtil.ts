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


export const deleteVideo = (videoId: number|string, accessToken: string)=>{
    return axios.delete(`${process.env.REACT_APP_BE_SERVER}${process.env.REACT_APP_DELETE_VIDEO_ENDPOINT}${videoId}`, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
        "Content-Type": "multipart/form-data",
      },
    })
}


export const uploadVideo = (formData: FormData, accessToken: string) => {
    return axios.post(
      `${process.env.REACT_APP_BE_SERVER}${process.env.REACT_APP_POST_VIDEO_ENDPOINT}`,
      formData,
      {
        headers: {
          Authorization: `Bearer ${accessToken}`,
          "Content-Type": "multipart/form-data",
        },
      }
    );
  };