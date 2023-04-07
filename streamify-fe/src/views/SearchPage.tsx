import  VideoBox  from "../components/VideoBox";
import { Container, Row } from "react-bootstrap";
import { Key, useEffect, useState } from "react";

import {searchVideosByKeywords } from "../util/videoUtil";
import { useSearchParams } from "react-router-dom";
import { searchChannelsByKeywords } from "../util/channelUtil";
import BoxGrid from "../components/BoxGrid";

export function SearchPage () {
  const [videos,setVideos] = useState([]);
  const [channels,setChannels] = useState([]);
  const [searchParams, setSearchParams] = useSearchParams();
  const keywords = searchParams.get("keywords");
  const searchVideos = searchParams.get("videos");
  const searchChannels = searchParams.get("channels");

  
  useEffect(()=>{
    if(searchVideos){
        searchVideosByKeywords(keywords!).then(res =>{
        setVideos(res.data);   
        }) 
    }

    if(searchChannels){
        searchChannelsByKeywords(keywords!).then(res =>{
            setChannels(res.data);
        }) 
    }
  },[])


    return (
        <Container className="my-auto">
            {searchVideos && (
                <>
                    <h1>Video Search results for: {keywords}</h1>   
                    <BoxGrid items={videos}/>
                </>
            )}
            {searchChannels && (
                <>
                    <h1>Channel Search results for: {keywords}</h1>
                    <BoxGrid items={channels}/>
                </>
            )}
        </Container>
    )
}