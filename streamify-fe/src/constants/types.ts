export type NavbarProps = {
    logged:boolean,
    isAdmin:boolean
}

export type VideoBoxProps = {
    video: Video
}

export type HomePageProps = {
    videos: Video[]
}

export type Video = {
    id: number,
    name: string,
    channelId: number,
    channel: Channel,
    url:String
}

export type Channel = {
    id: number,
    username: string,
    channelName: string
}