import { Card, Col } from "react-bootstrap";
import { Video, VideoBoxProps } from "../constants/types";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { getChannelDetails } from "../util/channelUtil";
import { useEffect, useState } from "react";

export default function VideoBox(props:{video:Video} ) {
  const navigate = useNavigate();
  const [channelName, setChanneName] = useState("")

  useEffect(()=>{
    getChannelDetails(props.video.channelId).then(res=>{
      setChanneName(res.data.channelName)
    })
  },[])


 

  return (
    <Col xs={6} md={4} lg={2} className="mb-4"
    onClick={() => navigate(`/video/${props.video.id}`, { state: { video: props.video } })}>
       <Card className="h-100 w-100 hover-overlay shadow-1-strong rounded">
        <Card.Header>{props.video.name}</Card.Header>
        <Card.Body>
          <p>Posted on: {channelName}</p>
        </Card.Body> 
      </Card>
    </Col>
  );
}
