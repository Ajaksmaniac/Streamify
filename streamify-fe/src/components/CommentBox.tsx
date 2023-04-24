import { Card, Col, Container, Row } from "react-bootstrap";
import {
  Channel,
  Comment,
  User,
  Video,
  VideoBoxProps,
} from "../constants/types";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { getChannelDetails } from "../util/channelUtil";
import { useEffect, useState } from "react";
import { getUserById } from "../util/userUtil";
import { useAuth } from "../hooks/useAuth";
import DeleteCommentButton from "./buttons/DeleteCommentButton";
// import { Container } from "react-dom";

export default function CommentBox(props: {
  comment: Comment;
  deleteCommentCallback: () => void;
  showDeleteCommentButton: boolean;
}) {
  const [commentPoster, setCommentPoster] = useState({} as User);
  const auth = useAuth();

  useEffect(() => {
    getUserById(props.comment.userId).then((res) => {
      setCommentPoster(res.data);
    });
  }, []);

  return (
    <Container className="border-top">
      <Row>
        <Col>
        <b> {commentPoster.username} at </b>
      <span>{props.comment.commented_at.toString()}</span>
      <p>{props.comment.content}</p>
        </Col>
        <Col>

        {props.showDeleteCommentButton && (
        <DeleteCommentButton
          commentId={props.comment.id}
          callback={props.deleteCommentCallback}
        />
      )}
        </Col>
      </Row>
    

      
    </Container>
  );
}
