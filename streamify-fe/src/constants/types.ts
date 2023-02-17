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
    postedBy: string,
    url:String
}