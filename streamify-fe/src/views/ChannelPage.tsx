import  VideoBox  from "../components/VideoBox";
import { Container, Row } from "react-bootstrap";
import { useEffect, useState } from "react";
import { getAllVideos, searchGetVideosForChannel } from "../util/videoUtil";
import { useLocation, useNavigate } from "react-router-dom";
import { Channel, Video } from "../constants/types";
import BoxGrid from "../components/BoxGrid";

export default function ChannelPage () {
    const location = useLocation();
    const channel = location.state.channel as Channel;
    const [videos,setVideos] = useState([] as Video[]);

  useEffect(()=>{
    searchGetVideosForChannel(channel.id).then(res=>{
      setVideos(res.data)
    })
  },[])

    return (
        <Container className="my-auto">
            <h1>Welcome to {channel.channelName}</h1>
            <h2>This channel is owned by {channel.username}</h2>
            <h3 className="border-top">Videos</h3>
            <BoxGrid items={videos}/>
            
        </Container>
    )
}