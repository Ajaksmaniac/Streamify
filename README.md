# Streamify

# Streamify BE
Spring boot application

## REST Auth controller Definition

POST `/auth/register`

- Doesn't need Authentication
- Creates a new User with user role
- Retrieves access token

Request Body

```
{
    "username":"testUser3",
    "password":"test"

}
```
Response
```
{
    "userId": "1",
    "accessToken": "...token..",
    "refreshToken": "...token..."

}
```
---
POST `/auth/login`

- Doesn't need Authentication
- Retrieves access token

Request Body

```
{
    "username":"testUser3",
    "password":"test"

}
```
Response
```
{
    "userId": "1",
    "accessToken": "...token..",
    "refreshToken": "...token..."

}
```
---
## REST User controller Definition

POST `/user/{id}/changePassword`

- Only Authenticated users can change password
- Users can only changed their own passwords
- Admin can change password of any user
- Retrieves access token

Params 
- newPassword: String
- username: String


Response
```
"Password Changed"
```

---
## REST Video controller Definition

GET `/video/id/{id}`
- Doesn't need Authentication
- Returns video binary
- Throws error message if video not found

Response
```
{
    "type": "Not-Found",
    "title": "Video Not Found",
    "status": 404,
    "detail": "Video with this id or name doesn't exist.",
    "instance": "/video/id/5"
}
```
---
GET `/video/all`
- Doesn't need Authentication
- Returns list of all video names

Response
```
[
    "video1",
    "video2",
    "video3"
]
```
---
GET `/video/details/id/{id}`
- Doesn't need Authentication
- Returns video details object
- Throws error message if video not found


Response
```
{
    "id": 1,
    "name": "first_video",
    "channel": "testChannel",
    "url": "/video/id/1"
}
```
---
GET `/video/details`
- Doesn't need Authentication
- Returns all video details object array

```
[
    {
        "id": 1,
        "name": "first_video",
        "channel": "testChannel",
        "url": "/video/id/1"
    },
    {
        "id": 2,
        "name": "second_video",
        "channel": "testChannel",
        "url": "/video/id/2"
    },
    {
        "id": 3,
        "name": "third_video",
        "channel": "testChannel",
        "url": "/video/id/3"
    }
]
```
---
DELETE `/video/id/{id}`
- Needs Authentication header with bearer token
- Authenticated user can only delete videos on his channel
- If user has admin role, it can delete any video
- Only users with content creator role can manage videos
- Deletes all comments associated with that video
- Returns Success message
- Returns error message if video doesn't exist


Response
```
"Video Deleted"
```
---
POST `/video`
- Needs Authentication header with bearer token
- Authenticated user can only post videos on his channel
- If user has admin role, it can delete videos on any channel
- Only users with content creator role can manage videos
- Returns Success message
- Returns error message if some of the body params are not valid

Body Params:
- file: File
- name: String,
- channelName: String
- description String


Response
```
"Video saved successfully."
```
---
PUT `/video/id/{id}`
- updates only Video and and its description
- Needs Authentication header with bearer token
- Authenticated user can only update videos on his channel
- If user has admin role, it can update videos on any channel
- Only users with content creator role can manage videos
- Returns Success message
- Returns error message if some of the body params are not valid

Body Params:
- name: String,
- channelName: String

Response

```
"Video Updated successfully."
```
---
## REST Channel controller Definition

GET `/channel/id/{id}`
- Doesn't need Authentication
- Returns channel details object with all videos on that channel
```
{
    "id": 1,
    "channelName": "testChannel",
    "username": "Ajaksmaniac",
    "videos": [
        {
            "id": 1,
            "name": "first_video",
            "channel": "testChannel",
            "url": "/video/id/1"
        },
        {
            "id": 2,
            "name": "second-video",
            "channel": "testChannel",
            "url": "/video/id/2"
        }
    ]
}

```
---
GET `/channel`
- Doesn't need Authentication
- Returns all channels

Response
```
[
    {
        "id": 1,
        "channelName": "testChannel1",
        "username": "user1",
    },{
        "id": 2,
        "channelName": "testChannel2",
        "username": "user2",
    }
]
```
---

POST `/channel`
- Creates a channel
- Needs Authentication header with bearer token
- User with Admin role can create channels for others
- Only users with content_creator role can create channels
- Users with content_creator role can only create channels for them selves
- Throw error if some of the parameters are not valid

Request body
```
{
    "channelName":"channel",
    "username": "user"
}
```
Response
```
"Channel saved"
```
---
DELETE `/channel/{id}`
- Deletes a channel
- Needs Authentication header with bearer token
- Users with Admin role can delete any channel
- Users with content_creator role can only delete their own channel
- Deletes all videos associated with that channel

Response
```
"Channel Deleted"
```

---

PUT `/channel`
- Updates a channel name and owner
- Needs Authentication header with bearer token
- Users with Admin role can update any channel
- If channel owner updated by Admin doesnt have content_creator role, that role would be automaticly assigned to the new channel owner
- Only Admin can change channel owners
- Users with content_creator role can only update their own channel

Request body
```
{
    "id": 1,
    "channelName": "testChannel1",
    "username": "Ajaksmaniac"
}
```

Response
```
"Channel Updated"
```

---
## REST Comment controller Definition


POST `/comment`

- Needs Authentication header with bearer token
- Any user can comment on any video
- Returns error message if some of the params are not valid

Request body
```
{
    "content": "Nice Video",
    "videoId": "1"
}
```
Response
```
{
    "Comment saved"
}
```
---
GET `/comment/video/{id}`
- Doesn't need authentication
- Returns all comment for given video

Response
```
[
    {
        "id": 1,
        "content": "Nice Video",
        "videoId": 1,
        "username": "user1",
        "commented_at": "2023-02-27"
    },
    {
        "id": 2,
        "content": "Very Nice Video",
        "videoId": 1,
        "username": "user2",
        "commented_at": "2023-02-24"
    },
]
```
---

GET `/comment/{id}`

- Doesn't need authentication
- Gets a comment by id

Response
```
{
    "id": 2,
    "content": "Very Nice Video",
    "videoId": 1,
    "username": "user2",
    "commented_at": "2023-02-24"
}
```
DELETE `/comment/{id}`
- Needs Authentication header with bearer token
- Comment poster can delete his comment
- Video poster can delete any comment
- Admin can delete any comment on any video

Response
```
{
    "Comment Deleted"
}
```


# Streamify FE
