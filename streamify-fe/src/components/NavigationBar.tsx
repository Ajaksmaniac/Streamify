import {
  Navbar,
  Nav,
  Form,
  FormControl,
  Button,
  Row,
  Col,
  Toast,
} from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { LiveNotification, NavbarProps } from "./../constants/types";
import { useEffect, useState } from "react";
import { useAuth } from "../hooks/useAuth";
import NewChannelButton from "./NewChannelButton";
import ChannelsDropdown from "./ChannelsDropdown";
import { getChannelDetailsByUserId } from "../util/channelUtil";
import NotificationsDropdown from "./NotificationsDropdown";
import { Notification } from "../constants/types";
import NotificationContainer from "./NotificationContainer";
import { parse } from "path";


export default function NavigationBar(props: NavbarProps) {
  const auth = useAuth();
  window.WebSocket = window.WebSocket || (window as any).MozWebSocket!;
  const [notifications, setNotifications] = useState([] as Notification[]);
  const [liveNotifications, setLiveNotifications] = useState(
    [] as LiveNotification[]
  );

  let socket: WebSocket;

  const navigate = useNavigate();
  const keywordsInitial = new URLSearchParams(window.location.search).get(
    "keywords"
  ) as string | number | string[] | undefined;
  const [disabled, setDisabled] = useState(false);
  const [keywords, setKeywords] = useState(
    keywordsInitial ? keywordsInitial : ""
  );
  const [searchVideos, setSearchVideos] = useState(
    new URLSearchParams(window.location.search).get("videos") ? true : false
  );
  const [userChannels, setUserChannels] = useState([]);
  const [searchChannels, setSearchChannels] = useState(
    new URLSearchParams(window.location.search).get("channels") ? true : false
  );

  const closeNotification =(index:number) =>{
    console.log(liveNotifications)
    if(liveNotifications.length == 1){
      setLiveNotifications([])
      return
    }
    const newArray = liveNotifications.splice(index,1)
    setLiveNotifications(newArray)

    console.log(liveNotifications)

  }

  useEffect(() => {
    if (
      (searchVideos == false && searchChannels == false) ||
      keywords === "" ||
      keywords == null
    ) {
      setDisabled(true);
    } else setDisabled(false);

    console.log(typeof notifications);
  }, [keywords, searchVideos, searchChannels, notifications]);

  const handleChange = (e: any) => {
    if (e.target.name == "keywords") setKeywords(e.target.value);
    if (e.target.name == "videos") setSearchVideos(!searchVideos);
    if (e.target.name == "channels") setSearchChannels(!searchChannels);
  };

  useEffect(() => {
    if (auth.user()) {
      getChannelDetailsByUserId(auth.user()?.id!)
        .then((res) => {
          setUserChannels(res.data);
        })
        .catch((e) => {
          setUserChannels([]);
        });

      if (!socket) {
        socket = new WebSocket(
          `ws://192.168.19.1:8082/notifications?userId=${auth.user()?.id}`
        );

        socket.onopen = () => {
          console.log("WebSocket connection established!");
        };

        socket.onmessage = (event) => {
          const receivedMessage = event.data;

          console.log(receivedMessage);
          const parsed = JSON.parse(receivedMessage);
          if (Array.isArray(parsed)) {
            parsed.forEach((el: Notification) => {
              setNotifications((notifications) => [
                ...notifications,
                ...[{ id:el.id,content: el.content, created_at: el.created_at,url: el.url }],
              ]);
            });
          } else {
            setNotifications((notifications) => [
              ...notifications,
              { id:parsed.id, content: parsed.content, created_at: parsed.created_at, url: parsed.url },
            ]);
            const liveNotification: LiveNotification = {
              notification: {
                id: parsed.id,
                content: parsed.content,
                created_at: parsed.created_at,
                url: parsed.url
              },
              show: true,
            };
            setLiveNotifications((liveNotifications) => [
              ...liveNotifications,
              liveNotification,
            ]);

            // toggleShowNotificationToast()
          }

          // console.log("Received message:", JSON.parse(receivedMessage));
        };

        socket.onclose = () => {
          console.log("WebSocket connection closed!");
        };
      }
    }
  }, []);

  return (
    <>
      <Navbar bg="dark" variant="dark">
        <a href="/">
          <img
            src={`${window.origin}/Logo.png`}
            className="img-fluid shadow-4"
            alt="..."
          />
        </a>

        <Form action="/search" className="mx-auto">
          <Row>
            <Form.Group as={Col}>
              <FormControl
                type="text"
                placeholder="Search Video"
                name="keywords"
                value={keywords}
                onChange={(e) => handleChange(e)}
                style={{ width: "500px" }}
              />
            </Form.Group>
            <Form.Group as={Col}>
              <Form.Check
                type="checkbox"
                // id='default-checkbox'
                label="Videos"
                className="text-primary"
                name="videos"
                onChange={(e) => handleChange(e)}
                checked={searchVideos}
              />
              <Form.Check
                type="checkbox"
                // id='default-checkbox'
                label="Channels"
                className="text-primary"
                name="channels"
                onChange={(e) => handleChange(e)}
                checked={searchChannels}
              />
            </Form.Group>
            <Form.Group as={Col}>
              <Button variant="outline-info" type="submit" disabled={disabled}>
                Search
              </Button>
            </Form.Group>
          </Row>
        </Form>

        {auth.user() ? (
          <Nav className="ml-auto">
            <Row>
              {/* Add become conntent creator button */}

              <Form.Group as={Col} className="p-2">
                {notifications && notifications.length > 1 ? (
                  <NotificationsDropdown notifications={notifications} />
                ) : (
                  <>
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      width="32"
                      height="32"
                      fill="red"
                      className="bi bi-bell"
                      viewBox="0 0 16 16"
                    >
                      <path d="M8 16a2 2 0 0 0 2-2H6a2 2 0 0 0 2 2zM8 1.918l-.797.161A4.002 4.002 0 0 0 4 6c0 .628-.134 2.197-.459 3.742-.16.767-.376 1.566-.663 2.258h10.244c-.287-.692-.502-1.49-.663-2.258C12.134 8.197 12 6.628 12 6a4.002 4.002 0 0 0-3.203-3.92L8 1.917zM14.22 12c.223.447.481.801.78 1H1c.299-.199.557-.553.78-1C2.68 10.2 3 6.88 3 6c0-2.42 1.72-4.44 4.005-4.901a1 1 0 1 1 1.99 0A5.002 5.002 0 0 1 13 6c0 .88.32 4.2 1.22 6z" />
                    </svg>
                  </>
                )}

                <Form.Text>
                  Hello {auth.user()?.role?.name}, {auth.user()?.username}
                </Form.Text>
                <span>&nbsp;&nbsp;</span>
                <Button
                  className="m-1"
                  variant="primary"
                  type="submit"
                  onClick={auth.signout}
                >
                  Logout
                </Button>
              </Form.Group>
            </Row>
          </Nav>
        ) : (
          <Nav className="ml-auto">
            <Row>
              <Form.Group as={Col} className="p-2">
                <Button
                  className="m-1"
                  variant="primary"
                  type="submit"
                  onClick={() => navigate("login")}
                >
                  Login
                </Button>

                <Button
                  variant="info"
                  type="submit"
                  onClick={() => navigate("register")}
                >
                  Sign Up
                </Button>
              </Form.Group>
            </Row>
          </Nav>
        )}
      </Navbar>
      {auth.user()?.role?.name === "content_creator" && (
        <Navbar bg="primary" variant="dark">
          <span>&nbsp;&nbsp;</span>

          <h3>Content Creator tools</h3>
          <span>&nbsp;&nbsp;</span>

          <NewChannelButton />
          <span>&nbsp;&nbsp;</span>

          {userChannels.length > 0 && (
            <ChannelsDropdown channels={userChannels} />
          )}
        </Navbar>
      )}
      {auth.user()?.role?.name === "admin" && (
        <Navbar bg="primary" variant="dark">
          <span>&nbsp;&nbsp;</span>

          <h3>Content Creator tools</h3>
          <span>&nbsp;&nbsp;</span>

          <NewChannelButton />
          <span>&nbsp;&nbsp;</span>

          {userChannels.length > 0 && (
            <ChannelsDropdown channels={userChannels} />
          )}
        </Navbar>
      )}
      <NotificationContainer notifications={liveNotifications} closeNotification={closeNotification}/>
    </>
  );
}
