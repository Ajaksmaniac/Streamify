import { Row } from "react-bootstrap";
import VideoBox from "./VideoBox";
import { Video, Channel } from "../constants/types";
import ChannelBox from "./ChannelBox";
import { isVideo, isChannel } from "../util/helpers";

export default function BoxGrid(props: { items: Video[] | Channel[] }) {
  const rows: any = [];
  for (let i = 0; i < props.items.length; i += 6) {
    rows.push(props.items.slice(i, i + 6));
  }

  return (
    <>
      {rows.map((row: any, rowIndex: any) => (
        <Row key={rowIndex}>
          {row.map((item: Channel | Video, itemIndex: number) => {
            if (isVideo(item)) return <VideoBox video={item} key={itemIndex} />;
            if (isChannel(item))
              return <ChannelBox channel={item} key={itemIndex} />;
          })}
        </Row>
      ))}
    </>
  );
}
