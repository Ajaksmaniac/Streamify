import React from 'react';
import { DropdownButton, ButtonGroup, Dropdown } from 'react-bootstrap';
import { Channel } from '../constants/types';
import { useNavigate } from 'react-router-dom';

const ChannelsDropdown = (props:{channels:Channel[]}) => {
    const navigate = useNavigate();
    return (
        <DropdownButton
            as={ButtonGroup}
            
            id={`dropdown-variants-info`}
            variant={"info"}
            title={"Your channels"}
          >
            {props.channels.map((c,index) =>{
                return (
                    <Dropdown.Item key={index} eventKey={index}
                    onClick={() => {
                        window.location.href = window.origin+"/channel/"+c.id
                    }}>

                    {c.channelName}</Dropdown.Item>
                )
            })}
 
          </DropdownButton>
    );
};

export default ChannelsDropdown;
