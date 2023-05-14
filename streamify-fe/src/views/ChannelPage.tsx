import VideoBox from "../components/VideoBox";
import { Container, Row } from "react-bootstrap";
import { useEffect, useState } from "react";
import { getAllVideos, searchGetVideosForChannel } from "../util/videoUtil";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import { Channel, Video } from "../constants/types";
import BoxGrid from "../components/BoxGrid";
import { getChannelDetails } from "../util/channelUtil";
import { useAuth } from "../hooks/useAuth";
import AddVideoModal from "../components/modals/AddVideoModal";
import DeleteChannelButton from "../components/buttons/DeleteChannelButton";
// import { log } from "console";

export default function ChannelPage() {
  // const location = useLocation();

  const [showModal, setShowModal]= useState(false)
  const auth = useAuth();

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

  const showDeleteChannelButton = (): boolean =>{
    if(auth.user()?.role?.name == 'admin') return true
    if(auth.user()?.username == channel.username) return true

    return false
  }

  return (
    <Container className="my-auto">
      {channel.id ? (
        <>
          <h1>Welcome to {channel.channelName}
          {showDeleteChannelButton() && 
          (
            <span>
              <DeleteChannelButton channelId={channel.id} callback={() => {window.location.replace(window.origin)}}/>
            </span>
          )
          }
          </h1>
          <h2>This channel is owned by {channel.username}</h2>
          <h3 className="border-top">Videos</h3>
          <BoxGrid items={videos} />
          {auth.user()?.username == channel.username && (
          
            <AddVideoModal channelId={channel.id} accessToken={auth.user()?.accessToken!} />
          )}
        </>
      ) : (
        <h1>Channel Doesnt exists</h1>
      )}
    </Container>
  );
}
