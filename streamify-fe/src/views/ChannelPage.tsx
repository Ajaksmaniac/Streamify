import VideoBox from "../components/VideoBox";
import { Container, Row } from "react-bootstrap";
import { useEffect, useState } from "react";
import { getAllVideos, searchGetVideosForChannel } from "../util/videoUtil";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import { Channel, Video } from "../constants/types";
import BoxGrid from "../components/BoxGrid";
import { getChannelDetails } from "../util/channelUtil";

export default function ChannelPage() {
  // const location = useLocation();
  const params = useParams() as any;
  // console.log(params);
  const [channel, setChannel] = useState({} as Channel);
  const [videos, setVideos] = useState([] as Video[]);

  useEffect(() => {
    getChannelDetails(params.id).then((res) => {
      setChannel(res.data);
    });

    searchGetVideosForChannel(params.id!).then((res) => {
      setVideos(res.data);
    });
  }, []);

  return (
    <Container className="my-auto">
      {channel.id ? (
        <>
          <h1>Welcome to {channel.channelName}</h1>
          <h2>This channel is owned by {channel.username}</h2>
          <h3 className="border-top">Videos</h3>
          <BoxGrid items={videos} />
        </>
      ) : (
        <h1>Channel Doesnt exists</h1>
      )}
    </Container>
  );
}
