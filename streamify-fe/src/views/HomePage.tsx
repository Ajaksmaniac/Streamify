import  VideoBox  from "../components/VideoBox";
import { Container, Row } from "react-bootstrap";
import { useEffect, useState } from "react";
import axios from "axios";
import { Video } from "../constants/types";
import { getAllVideos } from "../util/videoUtil";

export function HomePage () {
  const [videos,setVideos] = useState([]);
  
  useEffect(()=>{
    getAllVideos().then(res =>{
      setVideos(res.data);
    })
  },[])

    const rows = [];
    for (let i = 0; i < videos.length; i += 6) {
      rows.push(videos.slice(i, i + 6));
    }
  
    // const fetchChannel = (video: Video) =>{
    //   const res = axios.get(`"http://localhost:8080/channel/id/${video.channelId}`).then(res=>{
    //     console.log(res)
    //     return res;
    //   })
    //   return res.finally;
      
    // } 

    return (
        <Container className="my-auto">
            <h1>Recommended videos</h1>
            {rows.map((row, rowIndex) => (
          <Row key={rowIndex}>
            {row.map((video, videoIndex) => (
              <VideoBox key={videoIndex} video={video} />
            ))}
          </Row>
        ))}
            
        </Container>
    )
}