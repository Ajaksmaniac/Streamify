import React, { useEffect, useState } from 'react';
import { Container } from 'react-bootstrap';
import { useLocation, useNavigate } from "react-router-dom";
import ReactPlayer from 'react-player/lazy'
import { getChannelDetails } from '../util/channelUtil';
import { Channel, Comment, Video } from '../constants/types';
import { getCommentsForVideo } from '../util/commentUtil';
import CommentBox from '../components/CommentBox';
import AddComment from '../components/AddComment';
import { getVideoDetailsById } from '../util/videoUtil';
import DescriptionBox from '../components/DescriptionBox';
import { useAuth } from '../hooks/useAuth';
import CommentDeletedAlert from '../components/alerts/CommentDeletedAlert';
import DeleteVideoButton from '../components/buttons/DeleteVideoButton';


const VideoPage = () => {
    const auth = useAuth();
    const location = useLocation();
    const navigate = useNavigate();
    
    const videoDetailsId = location.pathname.split('/')[2];
    // console.log(videoDetailsId)
    // const video = location.state.video as Video;
    const [video,setVideo] = useState({} as Video)
    const [channel,setChannel] = useState({} as Channel); 
    const [comments,setComments] = useState([] as Comment[]); 
    const [showDeleteCommentAlert,setShowDeleteCommentAlert] = useState(false) 

    useEffect(()=>{
        getVideoDetailsById(videoDetailsId).then(res=>{
            console.log(res.data)
            setVideo(res.data)
        })
      },[])

    useEffect(()=>{ 
        if(video.url){
            getChannelDetails(video.channelId).then(res =>{
                setChannel(res.data);
            })

            reloadComments()
        }
        

      },[video])
      const reloadComments = () =>{
        console.log("reloading Comments")
        setComments([])
        getCommentsForVideo(video.id).then(res =>{
            setComments(res.data)
        })
    
      }
      const deleteComment = () =>{
        reloadComments()
        setShowDeleteCommentAlert(true)
      }

      const showDeleteCommentButton = (): boolean =>{
        if(auth.user()?.role?.name == 'admin') return true
        if(auth.user()?.username == channel.username) return true

        return false
      }

      const showDeleteVideo = (): boolean =>{
        if(auth.user()?.role?.name == 'admin') return true
        if(auth.user()?.username == channel.username) return true

        return false
      }

      const src = `${process.env.REACT_APP_BE_SERVER}${video.url}`;
    // console.log(video)
    return (
        <Container className="mt-2">
            {video.url && (
            <ReactPlayer url={src} playing={true}  controls width={"100%"} ></ReactPlayer>

            )}
            <h1>{video.name}
              {showDeleteVideo() && 
              (
                <span>
                    <DeleteVideoButton videoId={video.id} callback={() => {window.location.replace(window.origin);
}}/>
                </span>
              )}
            </h1>

            <h3>Posted By 
                <a 
                onClick={() => navigate(`/channel/${channel.id}`, { state: { channel: channel } })}
                >{channel.channelName}</a>
               {/* {
                  auth.user()?.subscribedChannels.find(c =>c.id ==  channel.id) ? (
                      <button className='btn btn-primary mb-3'>Unsubscribe</button>
                  ):(
                    <button className='btn btn-primary mb-3'>Subscribe</button>
                  )
                } */}
            </h3> 
            <DescriptionBox description={video.description}/>

            <h4>Comments for this video</h4>
            <div className='w-50'>
            {video.url && comments && (
                <>
                {comments.map(comment=>{
                return (
                 <CommentBox comment={comment} key={comment.id} deleteCommentCallback={deleteComment} showDeleteCommentButton={showDeleteCommentButton()}/>                
                 )
            })}
              {showDeleteCommentAlert && (
           <CommentDeletedAlert content="Comment Successfully Deleted" show={showDeleteCommentAlert}/> 

    )}

                </>
            )}
            {auth.user() && <AddComment videoId={video.id} callback={reloadComments} />}
            </div>
            
            
        </Container>
    );
};

export default VideoPage;