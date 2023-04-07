import { Container, Row } from "react-bootstrap";
import { useEffect, useState } from "react";
import { getAllVideos } from "../util/videoUtil";
import BoxGrid from "../components/BoxGrid";

export function HomePage () {
  const [videos,setVideos] = useState([]);
  
  useEffect(()=>{
    getAllVideos().then(res =>{
      setVideos(res.data);
    })
  },[])

    return (
        <Container className="my-auto">
            <h1>Recommended videos</h1>
           <BoxGrid items={videos}/>
            
        </Container>
    )
}