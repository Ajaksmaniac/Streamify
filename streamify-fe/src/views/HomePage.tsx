import { HomePageProps } from "../constants/types";
import  VideoBox  from "../components/VideoBox";
import { Col, Container, Row } from "react-bootstrap";

export function HomePage (props: HomePageProps) {

    const rows = [];
    for (let i = 0; i < props.videos.length; i += 6) {
      rows.push(props.videos.slice(i, i + 6));
    }
  
    return (
        <Container>
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