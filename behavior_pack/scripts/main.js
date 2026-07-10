import { world, system } from "@minecraft/server";

system.afterEvents.worldInitialize.subscribe(() => {
  world.afterEvents.entityDie.subscribe((event) => {
    const { deadEntity } = event;

    if (deadEntity.typeId === "minecraft:player") {
      deadEntity.dimension.playSound("death.custom", deadEntity.location);
    }
  });
});
