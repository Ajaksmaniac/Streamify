import React from 'react';
import { DropdownButton, ButtonGroup, Dropdown, NavDropdown } from 'react-bootstrap';
import { Channel } from '../constants/types';
import { useNavigate } from 'react-router-dom';
import { Notification } from '../constants/types';
import { deleteNotification } from '../util/notificationUtil';
import { useAuth } from '../hooks/useAuth';

const NotificationsDropdown = (props:{notifications:Notification[]}) => {
  const auth = useAuth();

  const handleDeleteNotification = (id: number, redirectUrl: string) =>{
    deleteNotification(id, auth.user()?.accessToken!).then(res=>{
      if(res.status == 200){
        window.location.href = redirectUrl

      }
    })
  }
    const navigate = useNavigate();

    return (
        <>
        <style>{`.dropdown-toggle::after { display: none !important; }`}</style>

        <NavDropdown
            as={ButtonGroup}
            color='black'
            id={`dropdown-variants-info`}
            align={{ lg: 'start' }}

            // variant={"info"}
            title={
                <>
               
                <svg
                xmlns="http://www.w3.org/2000/svg"
                width="32"
                height="32"
                fill="yellow"
                className="bi bi-bell-fill"
                viewBox="0 0 16 16"
              >
                 {/* <path d="M5.5 2A3.5 3.5 0 0 0 2 5.5v5A3.5 3.5 0 0 0 5.5 14h5a3.5 3.5 0 0 0 3.5-3.5V8a.5.5 0 0 1 1 0v2.5a4.5 4.5 0 0 1-4.5 4.5h-5A4.5 4.5 0 0 1 1 10.5v-5A4.5 4.5 0 0 1 5.5 1H8a.5.5 0 0 1 0 1H5.5z"/> */}
                <path d="M14 1a1 1 0 1 1-1 0 1 1 0 0 1 1 0z"/>
                <path d="M8 16a2 2 0 0 0 2-2H6a2 2 0 0 0 2 2zm.995-14.901a1 1 0 1 0-1.99 0A5.002 5.002 0 0 0 3 6c0 1.098-.5 6-2 7h14c-1.5-1-2-5.902-2-7 0-2.42-1.72-4.44-4.005-4.901z" />
                
              </svg>
              </>
            }

          >
            {props.notifications.map((c: Notification,index) =>{
                // console.log(c)
                return (
                    <Dropdown.Item key={index} eventKey={index} color='black'
                    onClick={() => {
                      handleDeleteNotification(c.id, c.url)

                     }}>

                        {c.content}
                    </Dropdown.Item>
                )
            })}
 
          </NavDropdown>
          </>
    );
    
};


export default NotificationsDropdown;

