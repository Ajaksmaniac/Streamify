import { Card, Col, Container } from "react-bootstrap";
import { Channel, Comment, User, Video, VideoBoxProps } from "../constants/types";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { getChannelDetails } from "../util/channelUtil";
import { useEffect, useState } from "react";
import { getUserById } from "../util/userUtil";
// import { Container } from "react-dom";

export default function CommentBox(props:{comment:Comment} ) {
  const [user,setUser] = useState({} as User)
console.log(props.comment)


  useEffect(()=>{
    getUserById(props.comment.userId).then(res=>{
      setUser(res.data)
    })
  },[])


 

  return (
    <Container className="border-top">

      <b> {user.username} at </b>
      <span>{props.comment.commented_at.toString()}</span>
      <p>{props.comment.content}</p>
    </Container>
  )
}
