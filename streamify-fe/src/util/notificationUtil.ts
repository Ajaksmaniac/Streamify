import axios from "axios"


export const deleteNotification = (notificationId:number, accessToken:string)=>{
    return axios.delete(`${process.env.REACT_APP_BE_SERVER}${process.env.REACT_APP_DELETE_NOTIFICATION_ENDPOINT}`,{
        headers: {
          Authorization: `Bearer ${accessToken}`,
          id: notificationId,

        },
      })
}
