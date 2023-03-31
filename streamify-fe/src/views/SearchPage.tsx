import  VideoBox  from "../components/VideoBox";
import { Container, Row } from "react-bootstrap";
import { Key, useEffect, useState } from "react";
import axios from "axios";
import { Channel, Video } from "../constants/types";
import { getAllVideos, searchVideosByKeywords } from "../util/videoUtil";
import { useSearchParams } from "react-router-dom";
import { searchChannelsByKeywords } from "../util/channelUtil";
import ChannelBox from "../components/ChannelBox";

export function SearchPage () {
  const [videos,setVideos] = useState([]);
  const [channels,setChannels] = useState([]);
  const [searchParams, setSearchParams] = useSearchParams();
  const keywords = searchParams.get("keywords");
  const searchVideos = searchParams.get("videos");
  const searchChannels = searchParams.get("channels");
  const videoRows:any[] = [];
  const channelRows = [];
  
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

    for (let i = 0; i < videos.length; i += 6) {
        videoRows.push(videos.slice(i, i + 6));
    }
     
    for (let i = 0; i < channels.length; i += 6) {
        channelRows.push(channels.slice(i, i + 6));
    } 

    return (
        <Container className="my-auto">
            {searchVideos && videos && videoRows && (
                <>
                    <h1>Video Search results for: {keywords}</h1>   
                    {/* {console.log(videoRows)} */}
                    {videoRows.map((row, rowIndex) => (
                    <Row key={rowIndex}>
                    {row.map((video: Video, videoIndex: Key | null | undefined) => (
                        <VideoBox key={videoIndex} video={video} />
                    ))}
                    </Row>
                ))}
                </>
            )}
            {searchChannels && (
                <>
                    <h1>Channel Search results for: {keywords}</h1>
                    {channelRows.map((row, rowIndex) => (
                    <Row key={rowIndex}>
                    {row.map((channel: Channel, channelIndex: Key | null | undefined) => (
                        <ChannelBox key={channelIndex} channel={channel} />
                    ))}
                    </Row>
                ))}
                </>
            )}
        </Container>
    )
}