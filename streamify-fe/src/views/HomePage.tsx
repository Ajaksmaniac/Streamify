import { Container, Row } from "react-bootstrap";
import { useEffect, useState } from "react";
import { getAllVideos } from "../util/videoUtil";
import BoxGrid from "../components/BoxGrid";

export function HomePage () {
  const [videos,setVideos] = useState([]);
  
  useEffect(()=>{
    if(videos.length < 1){
      getAllVideos().then(res =>{
        console.log(res.data)
        setVideos(res.data);
      })
    }
    
  },[])

    return (
        <Container className="my-auto">
            <h1>Recommended videos</h1>
           <BoxGrid items={videos}/>
            
        </Container>
    )
}