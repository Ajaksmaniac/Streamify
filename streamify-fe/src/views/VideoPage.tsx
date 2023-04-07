import React, { useEffect, useState } from 'react';
import { Container } from 'react-bootstrap';
import { useLocation, useNavigate } from "react-router-dom";
import ReactPlayer from 'react-player/lazy'
import { getChannelDetails } from '../util/channelUtil';
import { Channel, Comment, Video } from '../constants/types';
import { getCommentsForVideo } from '../util/commentUtil';
import CommentBox from '../components/CommentBox';


const VideoPage = () => {
    const location = useLocation();
    const navigate = useNavigate();

    const video = location.state.video as Video;

    const [channel,setChannel] = useState({} as Channel); 
    const [comments,setComments] = useState([] as Comment[]); 
    const src = `http://localhost:8080${video.url}`;

    useEffect(()=>{
        getChannelDetails(video.channelId).then(res =>{
            setChannel(res.data);
        })

        getCommentsForVideo(video.id).then(res =>{
            setComments(res.data)
        })
      },[])
    // console.log(video)
    return (
        <Container className="mt-2">
            <ReactPlayer url={src} playing={true}  controls width={"100%"} ></ReactPlayer>
            <h1>{video.name}</h1>
            <h3>Posted By 
                <a 
                onClick={() => navigate(`/channel/${channel.id}`, { state: { channel: channel } })}
                >{channel.channelName}</a>
            </h3> 

            <h4>Comments for this video</h4>
            {comments.map(comment=>{
                return (
                 <CommentBox comment={comment}/>                
                 )
            })}
        </Container>
    );
};

export default VideoPage;