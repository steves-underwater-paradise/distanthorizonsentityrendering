package io.github.steveplays28.distanthorizonsentityrendering.compat.distanthorizons.event;

import com.seibel.distanthorizons.api.DhApi;
import com.seibel.distanthorizons.api.methods.events.abstractEvents.DhApiAfterDhInitEvent;
import com.seibel.distanthorizons.api.methods.events.sharedParameterObjects.DhApiEventParam;
import com.seibel.distanthorizons.api.objects.math.DhApiVec3f;
import com.seibel.distanthorizons.api.objects.render.DhApiRenderableBox;

import java.awt.*;

public class DHERAfterDhInitEventHandler extends DhApiAfterDhInitEvent {
	@Override
	public void afterDistantHorizonsInit(DhApiEventParam<Void> dhApiEventParam) {
		var singleBoxGroup = DhApi.Delayed.renderRegister.createForSingleBox(
				new DhApiRenderableBox(new DhApiVec3f(0f, 80f, 0f), 10f, Color.CYAN));
		DhApi.Delayed.renderRegister.add(singleBoxGroup);
	}
}
