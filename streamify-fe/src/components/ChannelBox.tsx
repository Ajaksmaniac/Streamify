import { Card, Col } from "react-bootstrap";
import { Channel, Video, VideoBoxProps } from "../constants/types";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { getChannelDetails } from "../util/channelUtil";
import { useEffect, useState } from "react";

export default function ChannelBox(props:{channel:Channel} ) {
  const navigate = useNavigate();console.log(props.channel)

  // const [channel, setChanneName] = useState("")

  // useEffect(()=>{
  //   getChannelDetails(props.channel.id).then(res=>{
  //     setChanneName(res.data.channelName)
  //   })
  // },[])


 

  return (
    <Col xs={6} md={4} lg={2} className="mb-4"
    // onClick={() => navigate(`/video/${props.video.id}`, { state: { video: props.video } })}
    >
       <Card className="h-100 w-100">
        <Card.Body>
          <h4>{props.channel.channelName}</h4>
          <p>Owner:{props.channel.username}</p>
        </Card.Body> 
      </Card>
    </Col>
  );
}
