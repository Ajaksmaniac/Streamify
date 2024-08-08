import { Card, Col } from "react-bootstrap";
import { Channel, Video, VideoBoxProps } from "../constants/types";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { getChannelDetails } from "../util/channelUtil";
import { useEffect, useState } from "react";

export default function ChannelBox(props:{channel:Channel} ) {
  const navigate = useNavigate();
  
  return (
    <Col xs={6} md={4} lg={2} className="mb-4"
    onClick={() => navigate(`/channel/${props.channel.id}`, { state: { channel: props.channel } })}
    >
       <Card className="h-100 w-100" data-test-id={"channelBox"}>
        <Card.Body data-test-id={"channelBox"}>
          <h4>{props.channel.channelName}</h4>
          <p>Owner:{props.channel.username}</p>
        </Card.Body> 
      </Card>
    </Col>
  );
}
