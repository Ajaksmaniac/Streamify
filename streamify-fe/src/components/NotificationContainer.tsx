import React from 'react';
import { Toast, ToastContainer } from 'react-bootstrap';
import { LiveNotification, Notification } from '../constants/types';
import { useAuth } from '../hooks/useAuth';
import { deleteNotification } from '../util/notificationUtil';

const NotificationContainer = (props: { notifications: LiveNotification[], closeNotification: (index:number)=>void}) => {
  const auth = useAuth();

    const handleDeleteNotification = (id: number, redirectUrl: string) =>{
      deleteNotification(id, auth.user()?.accessToken!).then(res=>{
        if(res.status == 200){
          window.location.href = redirectUrl

        }
      })
    }
  const closeNotification = (index: number) =>{
    props.closeNotification(index)
  }
    return (
     
                <ToastContainer position="top-end" className="p-3" style={{ zIndex: 1 }}>
                {
        props.notifications.length > 0 && props.notifications.map((ln: LiveNotification, index: number) =>{
          return(
        <Toast
        onClose={()=>closeNotification(index)}
            show={ln.show}
            key={index}
            

          >
          <Toast.Header>
            <img
              src="holder.js/20x20?text=%20"
              className="rounded me-2"
              alt=""
            />
            <strong className="me-auto">Subscriber notification</strong>
            <small>11 mins ago</small>
          </Toast.Header>
          <Toast.Body onClick={() => {
                        handleDeleteNotification(ln.notification.id, ln.notification.url)
                     }}>{ln.notification.content} 
          </Toast.Body>
        </Toast>
          )
        })
      }
                </ToastContainer>
  
    );
};

export default NotificationContainer;