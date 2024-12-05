package edu.miu.TradingPlatform.controller.secure;

import edu.miu.TradingPlatform.domain.Assets;
import edu.miu.TradingPlatform.service.asset.AssetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/assets")
public class AssetController {

    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @GetMapping("/{assetId}")
    public ResponseEntity<Assets> getAssetByAssetId(@PathVariable Long assetId) throws Exception {
        Assets asset = assetService.getAssetById(assetId);
        return new ResponseEntity<>(asset, HttpStatus.OK);
    }

    @GetMapping("/coin/{coinId}/user")
    public ResponseEntity<Assets> getAssetByCoinId(@RequestHeader("Authorization") String jwtToken, @PathVariable String coinId) throws Exception {
        Assets asset = assetService.findAssetByCoinId(jwtToken, coinId);
        return new ResponseEntity<>(asset, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Assets>> getAllAssetForUser(@RequestHeader("Authorization") String jwtToken) throws Exception {
        List<Assets> assets = assetService.getAssetsByUserId(jwtToken);
        return new ResponseEntity<>(assets, HttpStatus.OK);
    }
}
