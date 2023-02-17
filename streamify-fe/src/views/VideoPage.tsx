import React from 'react';
import { Container } from 'react-bootstrap';
import { useLocation } from "react-router-dom";
import ReactPlayer from 'react-player/lazy'


const VideoPage = () => {
    const location = useLocation();
    const video = location.state.video;
    
    const src = `http://localhost:8080${video.url}`;

    return (
        <Container className="mt-2">
            <ReactPlayer url={src} playing={true}  controls width={"100%"} ></ReactPlayer>
            <h1>{video.name}</h1>
            <h3>Posted By:{video.postedBy}</h3> 
        </Container>
    );
};

export default VideoPage;