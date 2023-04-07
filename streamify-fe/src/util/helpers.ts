import { Video,Channel } from "../constants/types";

export function isVideoArray(array: any[]): array is Video[] {
    return Array.isArray(array) && array.every((item) =>
      item && typeof item === "object" && "id" in item && "name" in item && "channelId" in item && "url" in item
    );
  }
  
export function isChannelArray(array: any[]): array is Channel[] {
    return Array.isArray(array) && array.every((item) =>
      item && typeof item === "object" && "id" in item && "username" in item && "channelName" in item
    );
}

export function isVideo(object:any): object is Video{
    return object && typeof object === "object" && "id" in object && "name" in object && "channelId" in object && "url" in object;
}

export function isChannel(object:any): object is Channel{
    return object && typeof object === "object" && "id" in object && "username" in object && "channelName" in object
}