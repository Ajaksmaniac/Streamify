import  VideoBox  from "../components/VideoBox";
import { Container, Row } from "react-bootstrap";
import { useEffect, useState } from "react";
import axios from "axios";

export function HomePage () {
  const [videos,setVideos] = useState([]);
  
  useEffect(()=>{
    axios.get("http://localhost:8080/video/details").then(res =>{
      setVideos(res.data);
    })
  },[])

    const rows = [];
    for (let i = 0; i < videos.length; i += 6) {
      rows.push(videos.slice(i, i + 6));
    }
  
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