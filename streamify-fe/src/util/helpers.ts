import { Video,Channel, User } from "../constants/types";
import { subscribe,unscubscribe } from "./userUtil";
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

export function subscribeToChannel(user:User,channelId: number){
  return subscribe(user.id, channelId,user.accessToken!)
}

export function unsubscribeFromChannel(user:User,channelId: number){
  return unscubscribe(user.id, channelId,user.accessToken!)
}