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
    url:string,
    description: string
}

export type Channel = {
    id: number,
    username: string,
    channelName: string
}

export type Comment = {
    id: number,
    content: string,
    videoId: number,
    userId: number,
    commented_at: Date
}

export type User = {
    id:number,
    username?:string,
    role?: Role,
    accessToken?: string,
    refreshToken?: string,
    subscribedChannels: Channel[]

}
export type Role = {
    id:number,
    name:string
}
